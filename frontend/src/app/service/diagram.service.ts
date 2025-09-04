import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {DiagramDto} from "../models/diagram.dto";
import {Observable} from "rxjs";
import { environment } from '../../environments/enviroment';

@Injectable({ providedIn: 'root' })
export class DiagramService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  reduceDiagram(diagram: DiagramDto): Observable<DiagramDto> {
    return this.http.post<DiagramDto>(this.apiUrl, diagram);
  }
}
