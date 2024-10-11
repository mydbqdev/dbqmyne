import { Observable } from 'rxjs';

export interface ApplicationSession{
    username:string;
    token:string;
    dbquser?:Observable<boolean>;
    empEmail?:string;
    empName?:string;
    emp_code?:string;
    designation?:string;
    firstTimePwd?:string;
}