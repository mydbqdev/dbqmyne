import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { StartupComponent } from './common/startup/startup.component';

import { NgModule } from '@angular/core';

const routes: Routes = [
	{path:'',redirectTo:'/startup',pathMatch:'full'},
	{path:'startup',component:StartupComponent,pathMatch:'full'},
  
	{ path: 'signin', component: SigninComponent },
	{
		path: 'forbidden', component: ForbiddenComponent
	},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }