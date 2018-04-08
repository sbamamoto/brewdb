import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-source-detail',
  templateUrl: './source-detail.component.html',
  styleUrls: ['./source-detail.component.css']
})


export class SourceDetailComponent implements OnInit {

    source = {}

  constructor(private router:Router, private route:ActivatedRoute, private http:HttpClient) { }

  ngOnInit() {
    this.getSourceDetail(this.route.snapshot.params['id']);
  }

    getSourceDetail (id) {
        this.http.get ('http://localhost:8080/source/'+id).subscribe( data =>
            {
                this.source = data;
            });
    }
    
    deleteSource (id) {
        this.http.delete('http://localhost:8080/source/'+id).subscribe ( res =>
        {
            this.router.navigate(['sources']);
        }, (err) => {
            console.log(err);
        });
    }

}
