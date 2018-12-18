import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-brewer-detail',
  templateUrl: './brewer-detail.component.html',
  styleUrls: ['./brewer-detail.component.css']
})
export class BrewerDetailComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private route:ActivatedRoute) { }
  
  brewer: any;

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
        id = this.route.snapshot.params['id'];
    }
    else {
        id = '-1';
    }
    this.http.get('/api/brewer/' + id).subscribe(data => {
        console.log(data);
        this.brewer = data;
      }, (err) => {
        console.log(err);
      }
    );

    
  }
   saveMashTun(tunId, brewerId) {
     console.log("HALLO");
   }
}
