import { TestBed } from '@angular/core/testing';

import { CoverLetterService } from './cover-letter.service';

describe('CoverLetterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CoverLetterService = TestBed.get(CoverLetterService);
    expect(service).toBeTruthy();
  });
});
