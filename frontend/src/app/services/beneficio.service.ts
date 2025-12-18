import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio, TransferenciaDto } from '../models/beneficio.model';

@Injectable({ providedIn: 'root' })
export class BeneficioService {
  private http = inject(HttpClient);
  private readonly API = 'http://localhost:8080/api/v1/beneficios';

  list(): Observable<Beneficio[]> { return this.http.get<Beneficio[]>(this.API); }
  create(beneficio: Beneficio): Observable<any> { return this.http.post(this.API, beneficio); }
  delete(id: number): Observable<any> { return this.http.delete(`${this.API}/${id}`); }
  transfer(dto: TransferenciaDto): Observable<any> { return this.http.post(`${this.API}/transfer`, dto); }
}
