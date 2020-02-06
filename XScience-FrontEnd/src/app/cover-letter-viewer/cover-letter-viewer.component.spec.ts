import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoverLetterViewerComponent } from './cover-letter-viewer.component';

describe('CoverLetterViewerComponent', () => {
  let component: CoverLetterViewerComponent;
  let fixture: ComponentFixture<CoverLetterViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoverLetterViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoverLetterViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
