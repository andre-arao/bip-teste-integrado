import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Beneficio } from '../models/beneficio.model';
import { Transferencia } from '../models/transferencia.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {

  private apiUrl = 'http://localhost:8080/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  criar(beneficio: Beneficio): Observable<any> {
    return this.http.post(this.apiUrl, beneficio);
  }

  atualizar(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.apiUrl}/${id}`, beneficio);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  transferir(dto: Transferencia): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/transfer`, dto);
  }
}
