import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-brewer',
  templateUrl: './brewer.component.html',
  styleUrls: ['./brewer.component.css']
})


export class BrewerComponent implements OnInit {

  brewers: any;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
    console.log(this.router.url);
    this.http.get('/api/brewer').subscribe(data => {
      this.brewers = data;
      console.log(data);
    });
  }

}
