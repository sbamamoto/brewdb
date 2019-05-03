import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {

  userdata: any
  firstname: string
  active: boolean = false

  constructor(private user:UserService, private auth:AuthService ) { }

  ngOnInit() {
    this.auth.stateEmitter.subscribe(data => {
      this.active = data
    })
    this.user.getUserData().subscribe( data => {
      this.userdata = data
      this.firstname = data.firstname
      this.active = data.isActive
      console.log(this.firstname + "mmmmmmm");
    })
  }

}
