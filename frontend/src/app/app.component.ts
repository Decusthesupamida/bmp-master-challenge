import {ChangeDetectionStrategy, Component} from '@angular/core';
import {DiagramDto} from "./models/diagram.dto";
import {DiagramService} from "./service/diagram.service";
import {CommonModule} from "@angular/common";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {DiagramUploadComponent} from "./components/diagram-upload/diagram-upload.component";
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {MatDivider} from "@angular/material/divider";
import {DiagramViewerComponent} from "./components/diagram-viewer/diagram-viewer.component";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatIconModule} from "@angular/material/icon";
import {finalize} from "rxjs";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    MatTab,
    MatTabGroup,
    DiagramUploadComponent,
    MatCard, MatDivider,
    DiagramViewerComponent,
    MatIconModule,
    MatCardHeader, MatProgressSpinner,
    MatCardContent,
    MatCardTitle,
    MatCardSubtitle
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
  title = 'frontend';
  sourceDiagram?: DiagramDto;
  reducedDiagram?: DiagramDto;

  isLoading = false;
  error: string | null = null;

  constructor(private api: DiagramService) {}

  onDiagramParsed(diagram: DiagramDto) {
    this.sourceDiagram = diagram;
    this.resetReductionState();
    this.reduceViaBackend(diagram);
  }

  reduceViaBackend(diagram: DiagramDto) {
    this.isLoading = true;
    this.error = null;
    this.reducedDiagram = undefined;

    this.api.reduceDiagram(diagram)
    .pipe(
      finalize(() => this.isLoading = false)
    )
    .subscribe({
      next: res => {
        this.reducedDiagram = res;
      },
      error: err => {
        this.error = err.error?.message || 'An unknown error occurred';
      }
    });
  }

  public resetState(): void {
    this.sourceDiagram = undefined;
    this.reducedDiagram = undefined;
    this.isLoading = false;
    this.error = null;
  }

  private resetReductionState(): void {
    this.reducedDiagram = undefined;
    this.isLoading = false;
    this.error = null;
  }

}
