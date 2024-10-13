import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { StartupComponent } from './common/startup/startup.component';
import { NgModule } from '@angular/core';
import { SignupComponent } from './components/signup/signup.component';
import { HomeComponent } from './components/home/home.component';
import { ListingComponent } from './listing/listing/listing.component';

const routes: Routes = [
	{path:'',redirectTo:'/startup',pathMatch:'full'},
	{path:'startup',component:StartupComponent,pathMatch:'full'},
	{ path: 'signup', component: SignupComponent },
	{ path: 'signin', component: SigninComponent },
	{ path: 'home', component: HomeComponent },
	{ path: 'listing', component: ListingComponent },
	{
		path: 'forbidden', component: ForbiddenComponent
	},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }