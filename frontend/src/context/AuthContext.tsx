import React, { createContext, useContext, useEffect, useState } from 'react';
import { UserProfile, Role } from '@/types/auth';
import { supabase } from '@/services/supabase/supabaseClient';
import { mockDemoUser } from '@/services/api/mockData';

interface AuthContextType {
  user: UserProfile | null;
  role: Role;
  isLoading: boolean;
  isAuthenticated: boolean;
  login: (email: string, role?: Role) => Promise<void>;
  logout: () => Promise<void>;
  switchRole: (newRole: Role) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserProfile | null>(() => {
    const saved = localStorage.getItem('eduflow_user');
    return saved ? JSON.parse(saved) : mockDemoUser;
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    // Listen to real Supabase auth state change
    const { data: { subscription } } = supabase.auth.onAuthStateChange(async (_event, session) => {
      if (session?.user) {
        const profile: UserProfile = {
          id: session.user.id,
          email: session.user.email || 'user@eduflow.io',
          fullName: session.user.user_metadata?.full_name || session.user.email?.split('@')[0] || 'User',
          role: (session.user.user_metadata?.role as Role) || 'STUDENT',
          accountStatus: 'ACTIVE',
        };
        setUser(profile);
        localStorage.setItem('eduflow_user', JSON.stringify(profile));
      }
    });

    return () => {
      subscription.unsubscribe();
    };
  }, []);

  const login = async (email: string, selectedRole: Role = 'STUDENT') => {
    setIsLoading(true);
    try {
      // In demo mode or connected Supabase mode, provide seamless login
      const profile: UserProfile = {
        ...mockDemoUser,
        email,
        role: selectedRole,
        fullName: email.split('@')[0].toUpperCase(),
      };
      setUser(profile);
      localStorage.setItem('eduflow_user', JSON.stringify(profile));
    } finally {
      setIsLoading(false);
    }
  };

  const logout = async () => {
    await supabase.auth.signOut();
    setUser(null);
    localStorage.removeItem('eduflow_user');
  };

  const switchRole = (newRole: Role) => {
    if (user) {
      const updated = { ...user, role: newRole };
      setUser(updated);
      localStorage.setItem('eduflow_user', JSON.stringify(updated));
    }
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        role: user?.role || 'STUDENT',
        isLoading,
        isAuthenticated: !!user,
        login,
        logout,
        switchRole,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
