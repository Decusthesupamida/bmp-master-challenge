import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {DiagramDto} from "../../models/diagram.dto";
import {MatIcon, MatIconModule} from "@angular/material/icon";
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

@Component({
  selector: 'app-diagram-upload',
  standalone: true,
  imports: [
    CommonModule,
    MatIcon,
    MatFormField,
    FormsModule,
    MatIconModule,
    MatInput,
    MatButton,
    MatLabel,
    MatCardHeader,
    MatCard, MatCardTitle, MatCardSubtitle,
    MatCardContent, MatCardActions],
  templateUrl: './diagram-upload.component.html',
  styleUrl: './diagram-upload.component.scss'
})
export class DiagramUploadComponent {
  @Output() diagramParsed = new EventEmitter<DiagramDto>();
  @Output() resetRequested = new EventEmitter<void>();

  @Input() isDataLoaded = false;

  rawJson = '';

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) {
      return;
    }
    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.rawJson = reader.result as string;
    };
    reader.readAsText(file);
  }

  processDiagram(): void {
    if (!this.rawJson) {
      alert('JSON data is empty.');
      return;
    }
    try {
      const diagram = JSON.parse(this.rawJson) as DiagramDto;
      this.diagramParsed.emit(diagram);
    } catch (e) {
      console.error('JSON parsing error:', e);
      alert('Invalid JSON format.');
    }
  }

  onResetClick(): void {
    this.rawJson = '';
    this.resetRequested.emit();
  }
}
