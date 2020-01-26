import { TestBed } from '@angular/core/testing';

import { DocumentSearchTextService } from './document-search-text.service';

describe('DocumentSearchTextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DocumentSearchTextService = TestBed.get(DocumentSearchTextService);
    expect(service).toBeTruthy();
  });
});
