import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { BeneficioService } from './services/beneficio.service';
import { Beneficio } from './models/beneficio.model';
import { Transferencia } from './models/transferencia.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule
  ],
  templateUrl: './app.html'
})
export class App implements OnInit {

  beneficios: Beneficio[] = [];

  novoBeneficio: Beneficio = {
    nome: '',
    saldo: 0
  };

  transferencia: Transferencia = {
    fromId: 0,
    toId: 0,
    amount: 0
  };

  constructor(private beneficioService: BeneficioService) {
    console.log('🔥 App CONSTRUTOR EXECUTOU');
  }

  ngOnInit(): void {
    console.log('🔥 App ngOnInit EXECUTOU');
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    console.log('📡 Chamando listar...');
    this.beneficioService.listar().subscribe({
      next: res => {
        console.log('✅ Lista:', res);
        this.beneficios = res;
      },
      error: err => console.error('❌ Erro listar:', err)
    });
  }

  criar(): void {
    console.log('➕ Criando:', this.novoBeneficio);
    this.beneficioService.criar(this.novoBeneficio).subscribe({
      next: () => this.carregarBeneficios(),
      error: err => console.error('❌ Erro criar:', err)
    });
  }

  atualizar(b: Beneficio): void {
    if (!b.id) return;
    this.beneficioService.atualizar(b.id, b).subscribe(() => this.carregarBeneficios());
  }

  excluir(id?: number): void {
    if (!id) return;
    this.beneficioService.excluir(id).subscribe(() => this.carregarBeneficios());
  }

  transferir(): void {
    console.log('🔁 Transferindo:', this.transferencia);
    this.beneficioService.transferir(this.transferencia).subscribe({
      next: () => this.carregarBeneficios(),
      error: err => console.error('❌ Erro transferência:', err)
    });
  }
}
