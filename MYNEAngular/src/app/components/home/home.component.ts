import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';
import { SideNavMenuComponent } from '../sideBar/side-nav.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { AnnouncementService } from 'src/app/admin/announcements/service/announcements.service';
import { UserService } from 'src/app/common/service/user.service';
import { NotificationService } from 'src/app/common/shared/message/notification.service';
import { DefMenu } from 'src/app/common/shared/def-menu';
import { defMenuEnable } from 'src/app/common/shared/variables';
import { Announcements } from 'src/app/admin/announcements/models/announcements-model';
import { CompanyNotifyService } from 'src/app/common/service/company.notification.service';
import { AnnouncementsList } from 'src/app/common/models/announcements-list-model';
import { AuthService } from 'src/app/common/service/auth.service';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
	public defHomeMenu: DefMenu;
	public userNameSession: string = "";
	errorMsg: any = "";
	mySubscription: any;
	@ViewChild(SideNavMenuComponent) sidemenuComp;
	public rolesArray: string[] = [];
	notifyList:Announcements[]=[];
	ActiveAnnouncementList: AnnouncementsList[]=[];

	constructor( @Inject(defMenuEnable) private defMenuEnable: DefMenu,private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,private companyNotifyService:CompanyNotifyService,
		private spinner: NgxSpinnerService, private announcementService: AnnouncementService, private notifyService: NotificationService,private authService:AuthService) {
		this.userNameSession = userService.getUsername();
		this.defHomeMenu=defMenuEnable;
		if (userService.getUserinfo() != undefined) {
			this.rolesArray = userService.getUserinfo().previlageList;
		}
		this.router.routeReuseStrategy.shouldReuseRoute = function () {
			return false;
		};

		this.mySubscription = this.router.events.subscribe((event) => {
			if (event instanceof NavigationEnd) {
				// Trick the Router into believing it's last link wasn't previously loaded
				this.router.navigated = false;
			}
		});
	}

	ngOnDestroy() {
		if (this.mySubscription) {
			this.mySubscription.unsubscribe();
		}
	}
	ngOnInit() {
		if (this.userNameSession == null || this.userNameSession == undefined || this.userNameSession == '') {
			this.router.navigate(['/']);
		}

		this.getAllAnnouncementList();
		this.getNotificationList();
		
	}
	ngAfterViewInit() {
		this.sidemenuComp.expandMenu(1);
		this.sidemenuComp.activeMenu(1, '');
	}

	getAllAnnouncementList() {
		this.authService.checkLoginUserVlidaate();
		this.spinner.show();
		this.companyNotifyService.getAnnouncementsListForUser().subscribe((data) => {
			this.ActiveAnnouncementList = data.listObj;
			this.ActiveAnnouncementList.forEach(
				sd=>{sd.description=sd.description.substring(0,300)}
			);
			this.spinner.hide();
		  }
			, error => {
				if (error.error && error.error.message) {
					this.errorMsg = error.error.message;
					console.log("Error:" + this.errorMsg);
					this.notifyService.showError(this.errorMsg, "");
					this.spinner.hide();
				} else {
					this.spinner.hide();
					if (error.status == 500 && error.statusText == "Internal Server Error") {
						this.errorMsg = error.statusText + "! Please login again or contact your Help Desk.";
					} else {
						let str;
						if (error.status == 400) {
							str = error.error;
						} else {
							str = error.message;
							str = str.substring(str.indexOf(":") + 1);
						}
						console.log("Error:" + str);
						this.errorMsg = str;
					}
					if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
				}
			}
		);
	}
	getNotificationList(): any {
		this.authService.checkLoginUserVlidaate();
		this.spinner.show();
		this.companyNotifyService.getNotificationList().subscribe((data: any) => {
		  if(data.listObj.length >0){
			this.notifyList = Object.assign([],data.listObj);
			this.notifyList.forEach(
				sd=>{sd.description=sd.description.substring(0,200)}
			);
		  }
		  this.spinner.hide();
		},error =>{
		  this.spinner.hide();
		  if(error.status==403){
			this.router.navigate(['/forbidden']);
		  }else  if (error.error && error.error.message) {
			this.errorMsg =error.error.message;
			console.log("Error:"+this.errorMsg);
			this.notifyService.showError(this.errorMsg, "");
			this.spinner.hide();
		  } else {
			this.spinner.hide();
			if(error.status==500 && error.statusText=="Internal Server Error"){
			  this.errorMsg=error.statusText+"! Please login again or contact your Help Desk.";
			}else{
			  let str;
				if(error.status==400){
				str=error.error;
				}else{
				  str=error.message;
				  str=str.substring(str.indexOf(":")+1);
				}
				console.log("Error:"+str);
				this.errorMsg=str;
			}
			if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		  }
		}
		)
	  }
	  downloadFile(dowFile:Announcements) {
		this.authService.checkLoginUserVlidaate();
		var form_data = new FormData();
		for ( var key in dowFile ) {
	  form_data.append(key, dowFile[key]);
	  }
		const fileName = dowFile.announcementName.replace(' ',''); 
		this.companyNotifyService.downloadFile(form_data).subscribe(blob => {
		  const link = document.createElement('a');
		  link.href = URL.createObjectURL(blob);
		  link.download = `${fileName}.pdf`
		  link.click();
		},
		error => {
				if(error.status==403){
			 this.router.navigate(['/forbidden']);
		   }else if (error.error && error.error.message) {
			 this.errorMsg = error.error.message;
			 console.log("Error:" + this.errorMsg);
			 this.notifyService.showError(this.errorMsg, "");
				   } else {
					  if (error.status == 500 && error.statusText == "Internal Server Error") {
			   this.errorMsg = error.statusText + "! Please login again or contact your Help Desk.";
			 } else {
			   let str;
			   if (error.status == 400) {
				 str = error.error;
			   } else {
				 str = error.message;
				 str = str.substring(str.indexOf(":") + 1);
			   }
			   console.log("Error:" + str);
			   this.errorMsg = "File is not available or deleted";
			 }
			 if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		   }
		 });
	  }
}
