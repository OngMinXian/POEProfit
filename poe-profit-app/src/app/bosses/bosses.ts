import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

interface BossDTO {
  name: string;
  id: string;
  costInChaos: number;
  expectedValueInChaos: number;
  profitInChaos: number;
  icon: string;
}

@Component({
  selector: 'app-bosses',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule],
  templateUrl: './bosses.html',
  styleUrl: './bosses.css'
})
export class BossesComponent implements OnInit {
  bosses: BossDTO[] = [];
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' | 'unsorted' = 'unsorted';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<BossDTO[]>('http://localhost:8080/api/bosses')
      .subscribe(data => {
        this.bosses = data;
        this.sort('profitInChaos');
      });
  }

  goToBoss(id: string) {
    this.router.navigate(['/bosses', id]);
  }

  sort(column: keyof BossDTO) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'desc';
    }

    this.bosses.sort((a, b) => {
      const valueA = a[column];
      const valueB = b[column];

      if (valueA < valueB) return this.sortDirection === 'asc' ? -1 : 1;
      if (valueA > valueB) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });
  }

  getSortDirection(column: string): 'asc' | 'desc' | 'unsorted' {
    return this.sortColumn === column ? this.sortDirection : 'unsorted';
  }

  getProfitColor(value: number): string {
    if (value > 0) {
      const max = Math.max(...this.bosses.map(b => Math.abs(b.profitInChaos)));
      const postiveNormalizationConst = Math.min(1, Math.abs(value) / max)
      const positiveSat = 30 + postiveNormalizationConst * 70;
      return `hsl(147, ${positiveSat}%, 50%)`;
    } else {
      const min = Math.min(...this.bosses.map(b => Math.abs(b.profitInChaos)));
      const negativeNormalizationConst = Math.min(1, Math.abs(value) / min);
      const negativeSat = 30 + negativeNormalizationConst * 70;
      return `hsl(0, ${negativeSat}%, 50%)`;
    }
  }
}
