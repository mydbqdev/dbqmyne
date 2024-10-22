import { Injectable, Inject } from '@angular/core';
import {HttpClient,HttpHeaders,HttpErrorResponse} from '@angular/common/http';
import { MessageService } from 'src/app/message.service';
import { BASE_PATH } from '../shared/variables';
import { Observable, of} from 'rxjs';
import { ServiceHelper } from '../shared/service-helper';
import { Router } from '@angular/router';
import { ApplicationSession } from '../shared/model/application-session';
import { AuthService } from './auth.service';
import { AESEncryptDecryptHelper } from '../shared/AESEncryptDecryptHelper';
import { User } from '../shared/user';
import { UserService } from './user.service';
import { ResponseStore } from '../models/response.model';

@Injectable()
export class AppAuthService extends AuthService{
    
    public oDataBlockSize:number;
    public baseODataUrl:string;
    public sessionSnapshot:ApplicationSession;
    httpOptions ={
        headers :new HttpHeaders({'Content-Type':'application/json'})
    };
    constructor(private httpclient:HttpClient,private router:Router,private messageService:MessageService,@Inject(BASE_PATH) private basePath:string,private userService:UserService,private encryptDecryptHelper:AESEncryptDecryptHelper){
       super();
        this.sessionSnapshot=null;
        this.message='';
    }


    setSessionStore(res: ResponseStore):void{
        sessionStorage.setItem('user', res.userEmail);
        sessionStorage.setItem('token', 'Bearer ' + res.token);
    }

    public isLoggedIn(redirectUrl?:string):Observable<boolean>{
        if(this.sessionSnapshot && !(sessionStorage.getItem('user')==null)){
           return of(true);
        }else{
            if(redirectUrl!=='/startup')
            this.router.navigate(['/startup']);
            return of(false);
       }
    }

    public getAuthUser(user:User) : Observable<any>{
        console.log("user>>>"+user)
        const url1=this.basePath +'auth/login';
      //  let loginUser : User ={userEmail:user.userEmail,password:this.encryptDecryptHelper.encrypt(user.password)};
        let loginUser : User ={userEmail:user.userEmail,password:user.password};
        /* above line(line number :58) will be remove once we will implement decrypt password functionality in backend using request header.
           below line (line number : 61) will be uncommented.
        */
        //let loginUser : User ={userEmail:user.userEmail};password
        let encrypt=this.encryptDecryptHelper.encrypt(user.password);
        return this.httpclient.post<User>(
            url1,
            loginUser,
            {
                headers:ServiceHelper.buildHeadersAuth(encrypt),
                observe : 'body',
                withCredentials:true
            }
        );
    }

     public checkLoginUserOnServer() : Observable<ApplicationSession>{
         const url1=this.basePath +'checkLoginUser';
         return this.httpclient.post<ApplicationSession>(
             url1,
             '',
             {
                 headers:ServiceHelper.buildHeaders(),
                observe : 'body',
                withCredentials:true
             }
         );
     }

     public checkLoginUser() : void{
         var msg:string;
         this.sessionSnapshot =null;
         this.message ='';
         this.checkLoginUserOnServer().subscribe(
             (result)=>{
                this.sessionSnapshot = result;
                this.sessionSnapshot.username = result.userEmail;
                this.sessionSnapshot.token = result.token;
                this.userService.setUsername(result.userEmail);
                this.userService.setUserinfo(result);
                this.userService.setDbquser(true);

                let resp: ResponseStore={userEmail:result.userEmail,token:result.token};
                this.setSessionStore(resp);
                if(!this.sessionSnapshot.username){
                    this.router.navigateByUrl('/signin');
                }else if(result.firstTimePwd!=undefined && result.firstTimePwd!='Y'){
                    this.router.navigateByUrl('/first-time-chng-pwd')              
               }else{
                    this.router.navigateByUrl('/home'); 
               }
             },
             (err) =>{
                if(err.error && err.error.message){
                    msg=err.error.message;
                }else{
                msg='An error occured while processing your request.Please contact your Help Desk.';
                }
                this.message=msg;
                this.router.navigateByUrl('/signin');
             },
             () =>{
                 if(!this.sessionSnapshot){
                    msg='An error occured while processing your request.Please contact your Help Desk.';
                    this.message=msg;
                    this.router.navigateByUrl('/signin');
                 }
             }
         );
        
     }

     public checkLoginUserVlidaate() : void{
        var msg:string;
        this.sessionSnapshot =null;
        this.message ='';
        this.checkLoginUserOnServer().subscribe(
            (result)=>{
               this.sessionSnapshot = result;
               this.sessionSnapshot.username = result.userEmail;
               this.sessionSnapshot.token = result.token;
               this.userService.setUsername(result.userEmail);
               this.userService.setUserinfo(result);
               this.userService.setDbquser(true);

               let resp: ResponseStore={userEmail:result.userEmail,token:result.token};
               this.setSessionStore(resp);
            },
            (err) =>{
               if(err.error && err.error.message){
                   msg=err.error.message;
               }else{
               msg='An error occured while processing your request.Please contact your Help Desk.';
               }
               this.message=msg;
               this.router.navigateByUrl('/signin');
            },
            () =>{
                if(!this.sessionSnapshot){
                   msg='An error occured while processing your request.Please contact your Help Desk.';
                   this.message=msg;
                   this.router.navigateByUrl('/signin');
                }
            }
        );
       
    }

     public checkLogoutUserOnServer() : Observable<ApplicationSession>{
        const url1=this.basePath +'userlogout';
        return this.httpclient.post<ApplicationSession>(
            url1,
            '',
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }

     public checkLogout() : void{
        var msg:string;
        this.sessionSnapshot =null;
        this.message ='';
        sessionStorage.clear();
        this.checkLogoutUserOnServer().subscribe(
            (result)=>{ 
                   this.router.navigate(['/signin']); 
            },
            (err) =>{
               if(err.error && err.error.message){
                   msg=err.error.message;
               }else{
               msg='An error occured while processing your request.Please contact your Help Desk.';
               }
               this.message=msg;
               this.router.navigateByUrl('/signin');
            },
            () =>{
                if(!this.sessionSnapshot){
                   msg='An error occured while processing your request.Please contact your Help Desk.';
                   this.message=msg;
                   this.router.navigateByUrl('/signin');
                }
            }
        );
       
    }
    
    
    private errorHandler(error:HttpErrorResponse){
        return of(error.message || "server error");
        
    }

    private log(message:string){
        this.messageService.add(`AuthService:${message}`,'info');
    }

    private handleError<T>( operation ='operation',result?:T){
        return (error:any):Observable<T> => {
            console.error(error);
            this.log(`${operation} failed: ${error.message}`);

            return of(result as T);
        };
    }
    
}