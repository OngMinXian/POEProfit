import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

interface Cost {
  name: string;
  quantity: number;
  chaosValue: number;
  totalCost: number;
  icon: string;
}

interface Reward {
  name: string;
  probability: number;
  chaosValue: number;
  expectedValue: number;
  icon: string;
}

interface BossDTO {
  name: string;
  id: string;
  costInChaos: number;
  expectedValueInChaos: number;
  profitInChaos: number;
  costs: Cost[];
  rewards: Reward[];
  wikiUrl: string;
  imageUrl: string;
}

@Component({
  selector: 'app-boss-details',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule],
  templateUrl: './boss-details.html',
  styleUrls: ['./boss-details.css'],
})
export class BossDetailComponent implements OnInit {
  boss?: BossDTO;
  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' | 'unsorted' = 'unsorted';

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const bossId = this.route.snapshot.paramMap.get('id');
    if (bossId) {
      this.http.get<BossDTO>(`http://localhost:8080/api/bosses/${bossId}`)
        .subscribe(data => {
          this.boss = data;
          this.sort('expectedValue');
        });
    }
  }
  
  goBack(): void {
    this.router.navigate(['/bosses']);
  }

  sort(column: keyof Reward) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'desc';
    }

    this.boss?.rewards.sort((a, b) => {
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
}
