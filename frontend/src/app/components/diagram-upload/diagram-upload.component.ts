import {Component, EventEmitter, Input, Output, ChangeDetectionStrategy} from '@angular/core';
import {CommonModule} from "@angular/common";
import {DiagramDto} from "../../models/diagram.dto";
import {MatIconModule} from "@angular/material/icon";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader, MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";

@Component({
  selector: 'app-diagram-upload',
  standalone: true,
  imports: [
    CommonModule,
    MatFormField,
    FormsModule,
    MatIconModule,
    MatInput,
    MatButton,
    MatLabel,
    MatCardHeader,
    MatCard, MatCardTitle, MatCardSubtitle,
    MatCardContent, MatCardActions,
    MatSnackBarModule
  ],
  templateUrl: './diagram-upload.component.html',
  styleUrl: './diagram-upload.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DiagramUploadComponent {
  @Output() diagramParsed = new EventEmitter<DiagramDto>();
  @Output() resetRequested = new EventEmitter<void>();

  @Input() isDataLoaded = false;

  rawJson = '';

  constructor(private snackBar: MatSnackBar) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      return;
    }
    const file = input.files[0];

    const kbSize = 1024 * 1024;
    const maxSizeMb = 5;
    if (file.size > maxSizeMb * kbSize * kbSize) {
      this.snackBar.open(`File too large. Max ${maxSizeMb}MB.`, 'Close', { duration: 4000 });
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.rawJson = reader.result as string;
    };
    reader.readAsText(file);
  }

  processDiagram(): void {
    if (!this.rawJson) {
      this.snackBar.open('JSON data is empty.', 'Close', { duration: 3000 });
      return;
    }
    try {
      const diagram = JSON.parse(this.rawJson) as DiagramDto;
      this.diagramParsed.emit(diagram);
      this.snackBar.open('Diagram loaded.', 'Close', { duration: 2000 });
    } catch (e) {
      console.error('JSON parsing error:', e);
      this.snackBar.open('Invalid JSON format.', 'Close', { duration: 4000 });
    }
  }

  onResetClick(): void {
    this.rawJson = '';
    this.resetRequested.emit();
    this.snackBar.open('Cleared.', 'Close', { duration: 1500 });
  }
}
