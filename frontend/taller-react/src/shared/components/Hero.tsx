import { Searchbar } from "@components/Searchbar";

interface HeroProps {
	gradientClasses: string;
	buttonColor: string;
	searchPlaceHolder: string;
}

export function Hero({
	gradientClasses = "",
	buttonColor = "",
	searchPlaceHolder,
}: HeroProps) {
	return (
		<section
			className={`flex h-45 w-full flex-col items-center justify-center gap-4 ${gradientClasses}`}
		>
			{<Searchbar buttonColor={buttonColor} placeholder={searchPlaceHolder} />}
		</section>
	);
}
