import { HttpClient } from "@angular/common/http";
import { Injectable, Inject } from "@angular/core";
import { Observable } from "rxjs";
import { BASE_PATH } from '../shared/variables';

@Injectable({
    providedIn: 'root'
  })
  export class ForgotpasswordService {
    
    httpClient:any;
    baseUrl:string;

  constructor(
    private http:HttpClient,@Inject(BASE_PATH) private basePath:string
  ) { }
 
    
     postSendOTP(data:any): Observable<any> {
      return this.http.post(this.basePath+"forgotpassword/default/sendOtp",data,{responseType:"text"});
  }
    postNewPassword(data:any):Observable<any>{
        return this.http.post(this.basePath+"forgotpassword/default/saveNewPassword",data ,{responseType:"text"});
    }

   
    public getEmpPersonalMail(email:String ): Observable<any> {
      return this.http.get(this.basePath+'forgotpassword/default/getEmpPeronalMail/'+email );
    }
    
    changeNewPassword(data:any):Observable<any>{
      console.log("savedchanged password");
        return this.http.post(this.basePath+"forgotpassword/default/changePassWord",data );
    }

     
  }