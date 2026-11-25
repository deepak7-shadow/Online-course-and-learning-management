import { createClient } from '@supabase/supabase-js';

const supabaseUrl = import.meta.env.VITE_SUPABASE_URL || 'https://uwlyqosmckfwnmezajrf.supabase.co';
const supabaseAnonKey = import.meta.env.VITE_SUPABASE_ANON_KEY || 'sb_publishable_J04LCXG858cL5qAOobk83A_MOsf8KH_';

export const supabase = createClient(supabaseUrl, supabaseAnonKey);
