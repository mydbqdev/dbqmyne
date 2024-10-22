import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/common/service/auth.service';
import { User } from 'src/app/common/shared/user';
import { ResponseStore } from 'src/app/common/models/response.model';
@Component({
	selector: 'app-signup',
	templateUrl: './signup.component.html',
	styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

	firstname: string = '';
	email: string = '';
	lastname: string = '';
	zipcode: string = '';
	password: string = '';
	submitted=false;
	error: string = '';
	constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

	ngOnInit() {}

	doSignup() {
		this.submitted=true;	    
		if (this.email !== '' && this.email !== null && this.password !== '' && this.password !== null) {
			const user: User = { userEmail: this.email.toLowerCase(), password: this.password };
            this.submitted=false;
		}
	}

}
