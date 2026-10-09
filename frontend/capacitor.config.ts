// import type { CapacitorConfig } from '@capacitor/cli';

// const config: CapacitorConfig = {
//   appId: 'sn.sunuagri.app',
//   appName: 'SunuAgri',
//   webDir: 'dist/frontend/browser',
//   bundledWebRuntime: false,
// };

// export default config;
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'sn.sunuagri.app',
  appName: 'SunuAgri',
  webDir: 'dist/frontend/browser',

  server: {
    androidScheme: 'https',   // autorise HTTPS
    cleartext: true,          // autorise HTTP (pour localhost)
  },

  android: {
    allowMixedContent: true,  // autorise le mélange HTTP/HTTPS
  },
};

export default config;