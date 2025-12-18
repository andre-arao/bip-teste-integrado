import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from './services/beneficio.service';
import { Beneficio, TransferenciaDto } from './models/beneficio.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './app.html'
  })
export class AppComponent implements OnInit {
  private service = inject(BeneficioService);
  beneficios = signal<Beneficio[]>([]);
  novo = { nome: '', descricao: '', valor: 0, ativo: true };
  transf = { fromId: 0, toId: 0, amount: 0 };
  ngOnInit() { this.carregar(); }
  carregar() { this.service.list().subscribe(res => this.beneficios.set(res)); }
  salvar() { this.service.create(this.novo).subscribe(() => this.carregar()); }
  excluir(id: number) { this.service.delete(id).subscribe(() => this.carregar()); }
  transferir() { this.service.transfer(this.transf).subscribe(() => this.carregar()); }
}
