import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { httpFactory } from '@angular/http/src/http_module';

interface activeUser {
  firstname: string,
  lastname: string,
  userId: string,
  isActive: boolean
}

@Injectable()
export class UserService {

  userData: activeUser

  constructor(private http: HttpClient) { }

  getUserData() {
    return this.http.get<activeUser>("/api/auth")
  }

}
