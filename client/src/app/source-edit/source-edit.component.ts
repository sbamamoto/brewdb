import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-source-edit',
  templateUrl: './source-edit.component.html',
  styleUrls: ['./source-edit.component.css']
})
export class SourceEditComponent implements OnInit {

  source = {}

  constructor(private http:HttpClient, private router:Router, private route:ActivatedRoute) { }

  ngOnInit() {
      this.getSource(this.route.snapshot.params['id']);
  }

  getSource(id) {
      this.http.get('http://localhost:8080/source/'+id).subscribe( data => {
          this.source = data;
      });
  }
  
    updateSource(id) {
        this.http.put('http://localhost:8080/source/'+id, this.source).subscribe (res => { 
            let id = res['id'];
            this.router.navigate(['/source-details' , id]);
        }, (err) => {
            console.log(err);
        });
    }
} 