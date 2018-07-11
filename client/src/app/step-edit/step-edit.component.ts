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
  step: any;
  receiptId: string;
  stepId: string;

  ngOnInit() {

    if (this.route.snapshot.params['id'] != null) {
      this.stepId = this.route.snapshot.params['id'];
    }
    else {
      this.stepId = '-1';
    }

    if (this.route.snapshot.params['receiptId'] != null) {
      this.receiptId = this.route.snapshot.params['receiptId'];
    }
    else {
      this.receiptId = '-1';
    }

    this.http.get('http://localhost:8080/step/' + this.stepId).subscribe(data => {
      console.log(data);
      this.step = data;
      console.log(this.step.stepType);
    });
  }

  saveStep(id) {
    this.step.receiptId=this.receiptId;
    console.log(" ### "+this.stepId);
    if (id == "-1") {
      this.http.post('http://localhost:8080/step', [this.step]).subscribe(res => {
        console.log("step sent");
        console.log(this.step);
        let id = res['id'];
        this.router.navigate(['/steps/'+this.receiptId]);
      }, (err) => {
        console.log(err);
      }
      );
    }
    else {
      this.http.put('http://localhost:8080/step/' + id, [this.step]).subscribe(res => {
        let id = res['id'];
        this.router.navigate(['/steps/'+this.receiptId]);
      }, (err) => {
        console.log(err);
      });
    }

  }
}