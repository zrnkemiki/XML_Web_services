import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorDocumentManagerComponent } from './editor-document-manager.component';

describe('EditorDocumentManagerComponent', () => {
  let component: EditorDocumentManagerComponent;
  let fixture: ComponentFixture<EditorDocumentManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorDocumentManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorDocumentManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
