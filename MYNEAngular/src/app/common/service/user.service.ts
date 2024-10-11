import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserInfo } from '../shared/model/userinfo.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) {
  }
  public username :string;
  public dbquser:boolean;
  public userinfo:UserInfo;

  getDbquser(){
      return this.dbquser;
  }

  setDbquser(_dbquser:boolean){
      this.dbquser=_dbquser;
  }
  getUsername(){
      return this.username;
  }

  setUsername(_username:string){
      this.username=_username;
  }

  getUserinfo(){
      return this.userinfo;
  }

  setUserinfo(_userinfo:UserInfo){
      this.userinfo=_userinfo;
  }
  

}
