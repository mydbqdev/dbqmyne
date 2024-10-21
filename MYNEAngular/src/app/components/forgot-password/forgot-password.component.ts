import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/common/service/auth.service';
import { User } from 'src/app/common/shared/user';
import { ResponseStore } from 'src/app/common/models/response.model';
@Component({
	selector: 'app-forgot-password',
	templateUrl: './forgot-password.component.html',
	styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

	email: string = '';
	newPassword: string = '';
	confirmPassword: string = '';
    stepPassword: number=0;
	submitted=false;
	submittedVerifyCode=false;
	submittedResetPwd=false;
	error: string = '';
	constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

	ngOnInit() {

	}

	doSignin() {
		this.submitted=true;    
		if (this.email !== '' && this.email !== null) {		
			const user: User = { empEmail: this.email.toLowerCase()};
            this.submitted=false;
			
		}
	}

	nextStep(id:number){
		this.submitted=false;
	this.submittedVerifyCode=false;
	this.submittedResetPwd=false;
	 if(id==1){
		if (this.email !== '' && this.email !== null) {		
            this.submitted=false;
			this.doSignin();
		}else{
			this.submitted=true; 
			return;
		}
	 }else if(id==3){
		if (this.newPassword !== '' && this.newPassword !== null && this.confirmPassword !== '' && this.confirmPassword !== null) {		
            this.submittedResetPwd=false;
			this.doSignin();
		}else{
			this.submittedResetPwd=true; 
			return;
		}
	 }	
     this.stepPassword=id;
	}

}
