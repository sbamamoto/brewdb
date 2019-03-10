import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AuthService } from './auth.service';
import { ConditionalExpr } from '@angular/compiler';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor (private auth: AuthService, private router: Router) {
    
  }

  canActivate( next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    console.log("guard active")
    if (this.auth.isLoggedIn.isActive) {
      return true
    }
    else {
      this.router.navigate(['/login'], {
        queryParams: {
          return: state.url
        }
      });
      return false;
    }
  }
}
