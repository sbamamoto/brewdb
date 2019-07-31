import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user.service';

@Component({
  selector: 'app-storage',
  templateUrl: './storage.component.html',
  styleUrls: ['./storage.component.css']
})
export class StorageComponent implements OnInit {

  materials:any;
  response:any;

  constructor(private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private userData: UserService
  ) {

  }

  ngOnInit() {
    // only can access storage if logged in
      this.http.get('/api/storage').subscribe(data => {
          console.log(' ##ngoninit## ' + data);
          this.response = data;
          console.log (this.response.materials)
          this.materials = this.response.materials;
        });
  }
}
