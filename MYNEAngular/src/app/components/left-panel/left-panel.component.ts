import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserService } from 'src/app/common/service/user.service';
import { DefMenu } from 'src/app/common/shared/def-menu';
import { defMenuEnable } from 'src/app/common/shared/variables';
import { AuthService } from 'src/app/common/service/auth.service';

@Component({
	selector: 'app-left-panel',
	templateUrl: './left-panel.component.html',
	styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit, AfterViewInit {
	public defHomeMenu: DefMenu;
	public userNameSession: string = "";
	errorMsg: any = "";
	mySubscription: any;

	constructor( @Inject(defMenuEnable) private defMenuEnable: DefMenu,private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,
		private spinner: NgxSpinnerService, private authService:AuthService) {
		this.router.routeReuseStrategy.shouldReuseRoute = function () {
			return false;
		};

		this.mySubscription = this.router.events.subscribe((event) => {
			if (event instanceof NavigationEnd) {
				// Trick the Router into believing it's last link wasn't previously loaded
				this.router.navigated = false;
			}
		});
		this.authService.checkLoginUserVlidaate();
	}

	ngOnDestroy() {
		if (this.mySubscription) {
			this.mySubscription.unsubscribe();
		}
	}
	ngOnInit() {
		//if (this.userNameSession == null || this.userNameSession == undefined || this.userNameSession == '') {
		//	this.router.navigate(['/']);
		//}
	}
	ngAfterViewInit() {

	}
}
