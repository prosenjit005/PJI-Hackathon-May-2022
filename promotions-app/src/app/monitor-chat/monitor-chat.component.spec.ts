import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitorChatComponent } from './monitor-chat.component';

describe('MonitorChatComponent', () => {
  let component: MonitorChatComponent;
  let fixture: ComponentFixture<MonitorChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonitorChatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitorChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
