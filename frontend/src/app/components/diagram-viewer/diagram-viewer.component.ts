import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {DiagramDto} from "../../models/diagram.dto";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatList, MatListItem} from "@angular/material/list";
import {Edge, NgxGraphModule, Node} from "@swimlane/ngx-graph";
import {CommonModule, NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MatLine} from "@angular/material/core";

@Component({
  selector: 'app-diagram-viewer',
  standalone: true,
  imports: [
    CommonModule,
    MatCard,
    MatCardTitle,
    MatList,
    MatListItem,
    NgxGraphModule,
    MatIconModule,
    NgIf,
    MatLine,
    MatCardHeader,
    MatCardContent
  ],
  templateUrl: './diagram-viewer.component.html',
  styleUrl: './diagram-viewer.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DiagramViewerComponent {
  nodes: Node[] = [];
  links: Edge[] = [];

  trackNode = (_: number, n: Node) => n.id;
  trackEdge = (_: number, e: Edge) => `${e.source}-${e.target}-${e.id}`;

  private _diagram?: DiagramDto;

  @Input()
  set diagram(value: DiagramDto | undefined) {
    this._diagram = value;

    if (value && value.nodes && value.edges) {
      this.transformDataForGraph(value);
    } else {
      this.nodes = [];
      this.links = [];
    }
  }

  get diagram(): DiagramDto | undefined {
    return this._diagram;
  }

  private transformDataForGraph(diagram: DiagramDto): void {
    this.nodes = diagram.nodes.map(n => ({
      id: String(n.id),
      label: n.name || String(n.id),
      number: +n.id
    }));

    this.links = diagram.edges.map(e => ({
      source: String(e.from),
      target: String(e.to),
    }));
  }

}
