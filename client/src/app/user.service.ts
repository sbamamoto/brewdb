import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { httpFactory } from '@angular/http/src/http_module';

interface userData {
  firstname: string,
  lastname: string,
  userId: string,
  isActive: boolean
}

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUserData() {
    return this.http.get<userData>('/api/login')
  }

}
