// useAuthStore.ts
import {create} from 'zustand';
 
interface AuthState {
  token: string | null;
  setToken: (token: string) => void;
  clearToken: () => void;
  isPostSuccessful: boolean;
  setPostSuccess: (status: boolean) => void;
}
 
const useAuthStore = create<AuthState>((set) => ({
  token: null,
  setToken: (token) => set({ token }),
  clearToken: () => set({ token: null }),
  isPostSuccessful: false, // New state to track post success
  setPostSuccess: (status) => set({ isPostSuccessful: status }),
}));
 
export default useAuthStore;
 
 