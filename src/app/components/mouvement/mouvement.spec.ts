import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Mouvement } from './mouvement';

describe('Mouvement', () => {
  let component: Mouvement;
  let fixture: ComponentFixture<Mouvement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Mouvement],
    }).compileComponents();

    fixture = TestBed.createComponent(Mouvement);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
