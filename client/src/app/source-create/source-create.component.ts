import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-source-create',
  templateUrl: './source-create.component.html',
  styleUrls: ['./source-create.component.css']
})

export class SourceCreateComponent implements OnInit {

  source = {};

  constructor(private http:HttpClient, private router:Router) { }

    ngOnInit() {
    }

    saveSource() {
        this.http.post ('http://localhost:8080/source', this.source).subscribe(res => {
                let id = res['id'];
                this.router.navigate(['/source-details/', id]);
           }, (err) => {
                console.log(err);
            }
        );  
    }
}
