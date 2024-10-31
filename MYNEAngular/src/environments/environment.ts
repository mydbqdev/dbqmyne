// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  basePath:'https://myne-api-qa.dbqportal.com/v1/',
  version:'1.0.0-04-05-2020-11:41:00',
  oDataBlockSize:100,
  defMenuEnable:{
    "personalMenu":true,
    "personalSubMenu":{
      "personalDetail":true,
      "address":true,
      "education":true,
      "prevEmployer":true,
      "communication":true,
      "familyDetails":true,
      "idcardRequest":true,
      "passport":true,
      "nomineeDetails":true,
      "myPayslip":true,
      "form16":true,
      "pfStatement":true
    },
    "employmentMenu":true,
    "employmentSubMenu":{
      "reportingManager":true,
      "dojWorkExperience":true,
      "currentWorkingLocation":true,
      "myProject":true,
      "myCompensation":true,
      "myPerformance":true
    },
    "accountMenu":true,
    "accountSubMenu":{
      "account":true,
      "bank":true,
      "pan":true,
      "uan":true
    },
    "lmsMenu":true,
    "lmsSubMenu":{
      "myLeaveDashboard":true,
      "holidayCalenders":true,
      "rmApprovals":true,
    },
    "emsMenu":true,
    "emsSubMenu":
    {
      "mySeperation":true,
      "emsApprovals":true,
      "emsSettlement":true,
      "empSeperation":true,
    },
    "reportMenu":true,
    reportSubMenu:{
      "employmentAddressProof":true,
    },
    "taxdecMenu":true,
    "taxdecSubMenu":{
      "itDeclaration":true,
      "itDeclarationApproval":true,
      "itDeclarationReports":true,
      "tdsReports":true,
    },
    "adminMenu":true,
    "adminSubMenu":{
      "configurationUpload":true,
      "uploadFiles":true,
      "employeeSearch":true,
      "backgroundVerification":true,
      "talentAcquisition":true,
      "iOnboard":true,
      "scheduler":true,
      "projectAssignation":true,
      "performanceRating":true,
      "compensation":true,
      "compensationUpload":true,
      "compensationApproval":true,
      "financeTime":true,
      "announcements":true,
      "seasonEmail":true
    },
    "superadminMenu":true,
    "superadminSubMenu":{
      "employeeInformation":true,
      "bandAndDesignation":true,
      "requirementApprovals":true,
      "employeeBGVInfo":true,
      "appraisal":true,
      "userMaster":true,
      "approvals":true
    },
    "timesheetMenu":true,
    "timesheetSubMenu":{
      "myTimesheetDashboard":true,
      "employeesTimesheet":true,
      "myShiftTime":true,
      "employeeShiftTime":true,
      "myCompoffApply":true,
      "employeeCompoffApproval":true,
    },
    "adminreportMenu":true,
    "adminReportSubMenu":{
      "payslipSalaryReports":true,
      "projectReports":true,
      "leaveBalanceReports":true,
      "resignationReports":true,
      "employeeNomineeReports":true,
      "employeeListReports":true,
      "officeTimeSheetReports":true,
      "timeSheetReports":true,
      "compensationReports":true,
      "financeReports":false,
      "salaryAccountDetailsReports":true
    },
    "approvalMenu":true,
    "approvalSubMenu":{
      "adminApprovals":true,
      "bankDetailsApprovals":true,
    },
    "financeAndAccount":true,
    "financeAndAccountSubMenu":{
       "processPayslip":true,
       "employeePayslipData":true,
    },"settingsMenu":true,
    "settingsSubMenu":{
      "pfSettings":true,
      "annexureType":true,
      "annexureConfiguration":true,
      "siteConfig":false,
      "branchInfo":true,
      "bandAndDesignation":true,
      "roleMaster":true,
      "emailConfig":true,
      "reportTextContent":true,
      "settingsConfig":true,
      "settingsTDS":true,
    }
  },
  firebaseConfig: {
    apiKey: 'AIzaSyDwZKE-Y7hYvBIdRl7p4j_ui4AJveo1QMo',
    authDomain: 'zoy-mobile-app.firebaseapp.com',
    projectId: 'zoy-mobile-app',
    storageBucket: 'zoy-mobile-app.appspot.com',
    messagingSenderId: '694212252477',
    appId: '1:694212252477:web:3627a2a382cc57be46eb54',
    measurementId: 'G-GHJ6WR7MR9',
    vapidKey: 'BMVwCKtR3IDXmifTMlPun_n94q35ffZNgqSPBPbA50ohOCm_YtorrjFUoEISYC9mTgJdDWyglewQOnGuOm6PVn0'
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
