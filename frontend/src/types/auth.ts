export type Role = 'STUDENT' | 'INSTRUCTOR' | 'ADMIN';
export type AccountStatus = 'ACTIVE' | 'SUSPENDED';

export interface UserProfile {
  id: string;
  email: string;
  fullName: string;
  avatarUrl?: string;
  role: Role;
  accountStatus: AccountStatus;
  bio?: string;
  headline?: string;
  createdAt?: string;
}

export interface AuthState {
  user: UserProfile | null;
  token: string | null;
  isLoading: boolean;
  isAuthenticated: boolean;
}
