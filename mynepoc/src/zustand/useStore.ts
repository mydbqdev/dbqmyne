// store.ts
import {create} from 'zustand';

export interface UserDetails {
  userId: string;
  userEmail: string;
  userFirstName: string;
  userLastName: string;
  zipCode: string;
}

export interface StoreState {
  userDetails: UserDetails | null; // User details can be null initially
  setUserDetails: (details: UserDetails) => void; // Function to set user details
}

// Create Zustand store
const useStore = create<StoreState>((set) => ({
  userDetails: null, // Initial state
  setUserDetails: (details) => set({ userDetails: details }), // Update user details
}));

export default useStore;
