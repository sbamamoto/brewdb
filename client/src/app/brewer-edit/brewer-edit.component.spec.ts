import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrewerEditComponent } from './brewer-edit.component';

describe('BrewerEditComponent', () => {
  let component: BrewerEditComponent;
  let fixture: ComponentFixture<BrewerEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrewerEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrewerEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
