import type { ReactNode } from "react";

interface HeroProps {
	children: ReactNode;
}

export function Hero({ children }: HeroProps) {
	return (
		<section className="flex h-45 w-full flex-col items-center justify-center gap-4 bg-linear-to-r from-(--hero-from) from-20% via-(--hero-via) to-(--hero-to)">
			{children}
		</section>
	);
}
