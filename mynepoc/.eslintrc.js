module.exports = {
  'eslint.workingDirectories': [
    {directory: 'client/', changeProcessCWD: true},
    {directory: 'server/', changeProcessCWD: true},
  ],
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: 2018,
    sourceType: 'module',
    project: './client/tsconfig.json',
  },
};
