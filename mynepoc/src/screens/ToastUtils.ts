// ToastUtils.ts
import Toast from 'react-native-toast-message';

interface ToastOptions {
  type: 'success' | 'error' | 'info';
  text1: string;
  text2?: string;
}

export const showToast = ({type, text1, text2}: ToastOptions) => {
  Toast.show({
    type,
    position: 'top',
    text1: text1,
    text2: text2 || '',
    visibilityTime: 3000,
    autoHide: true,
    topOffset: 10,
    bottomOffset: 90,
  });
};
