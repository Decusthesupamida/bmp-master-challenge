import {ComponentFixture, TestBed} from "@angular/core/testing";
import {DiagramUploadComponent} from "./diagram-upload.component";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {DiagramDto} from "../../models/diagram.dto";

describe('DiagramUploadComponent', () => {
  let component: DiagramUploadComponent;
  let fixture: ComponentFixture<DiagramUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiagramUploadComponent, NoopAnimationsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(DiagramUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should emit parsed diagram on valid JSON', () => {
    const payload: DiagramDto = {
      nodes: [{ id: '1', name: 'A', type: 'START' }],
      edges: []
    };
    const json = JSON.stringify(payload);

    const spy = jasmine.createSpy('diagramParsed');
    component.diagramParsed.subscribe(spy);

    component.rawJson = json;
    component.processDiagram();

    expect(spy).toHaveBeenCalledTimes(1);
    const [arg] = spy.calls.argsFor(0) as [DiagramDto];
    expect(arg.nodes.length).toBe(1);
    expect(arg.nodes[0].id).toBe('1');
  });

  it('should not emit on empty JSON', () => {
    const spy = jasmine.createSpy('diagramParsed');
    component.diagramParsed.subscribe(spy);

    component.rawJson = '';
    component.processDiagram();

    expect(spy).not.toHaveBeenCalled();
  });

  it('should not emit on invalid JSON', () => {
    const spy = jasmine.createSpy('diagramParsed');
    component.diagramParsed.subscribe(spy);

    component.rawJson = '{'; // guaranteed invalid JSON
    component.processDiagram();

    expect(spy).not.toHaveBeenCalled();
  });
});
