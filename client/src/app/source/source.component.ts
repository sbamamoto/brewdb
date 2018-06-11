import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-source',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})

export class SourceComponent implements OnInit {
    sources:any;

  constructor(private http: HttpClient) { }

  ngOnInit() {
this.http.get('http://localhost:8080/source').subscribe( data=> {
    this.sources = data;
});
  }

}
