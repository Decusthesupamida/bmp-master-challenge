import {ComponentFixture, TestBed} from '@angular/core/testing';
import {DiagramViewerComponent} from './diagram-viewer.component';
import {DiagramDto} from '../../models/diagram.dto';

describe('DiagramViewerComponent', () => {
  let component: DiagramViewerComponent;
  let fixture: ComponentFixture<DiagramViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiagramViewerComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(DiagramViewerComponent);
    component = fixture.componentInstance;
  });

  it('should map nodes and edges for graph', () => {
    const diagram: DiagramDto = {
      nodes: [
        { id: '1', name: 'Start', type: 'START' },
        { id: '2', name: 'Task', type: 'HUMAN_TASK' }
      ],
      edges: [
        { from: '1', to: '2' }
      ]
    };

    component.diagram = diagram;

    expect(component.nodes.length).toBe(2);
    expect(component.links.length).toBe(1);
    expect(component.nodes[0]).toEqual(jasmine.objectContaining({ id: '1', label: 'Start' }));
    expect(component.links[0]).toEqual(jasmine.objectContaining({ source: '1', target: '2' }));
  });

  it('should clear graph arrays when diagram is undefined', () => {
    component.diagram = undefined;
    expect(component.nodes).toEqual([]);
    expect(component.links).toEqual([]);
  });
}); 