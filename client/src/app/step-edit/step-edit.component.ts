import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-step-edit',
  templateUrl: './step-edit.component.html',
  styleUrls: ['./step-edit.component.css']
})
export class StepEditComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }
  stepData: any;
  receiptId: any;

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
      id = this.route.snapshot.params['id'];
    }
    else {
      id = '-1';
    }
    this.receiptId = this.route.snapshot.params['receipt'];
    this.http.get('http://localhost:8080/step?id='+id+"?receiptId="+this.receiptId).subscribe(data => {
      console.log(data);      
      this.stepData = data;
    });
  }

}
