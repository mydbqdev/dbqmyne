import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/common/service/auth.service';
import { User } from 'src/app/common/shared/user';
import { ResponseStore } from 'src/app/common/models/response.model';
@Component({
	selector: 'app-signin',
	templateUrl: './signin.component.html',
	styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

	username: string = '';
	password: string = '';
	rememberme:boolean=false;
    showPassword: any;
	isSignedin = false;
	submitted=false;
	error: string = '';
	constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

	ngOnInit() {
		if(JSON.parse(localStorage.getItem('rememberme'))!==null){
			this.username=localStorage.getItem('username');
			this.password=localStorage.getItem('userpwd');
			this.rememberme=JSON.parse(localStorage.getItem('rememberme'));
		}
	}

	doSignin() {
		console.log("username",this.username ,">>>>this.password",this.password)
		this.submitted=true;    
		if (this.username !== '' && this.username !== null && this.password !== '' && this.password !== null) {
			const user: User = { userEmail: this.username.toLowerCase(), password: this.password };
            this.submitted=false;
			this.authService.getAuthUser(user).subscribe((result) => {
				const res: ResponseStore = { userEmail: this.username.toLowerCase(), token: result.token };
				this.authService.setSessionStore(res);
			//	this.authService.checkLoginUser();
				this.router.navigate(['/home']);
			}, () => {
				this.error = 'Invalid credentials or something went wrong';
			});
		}// else {
		//	this.error = 'Username or Password is required';
		//}
	}

}
