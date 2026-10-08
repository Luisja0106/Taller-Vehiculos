interface itemAttribute {
	atributeLabel: string;
	atributeValue: string;
}

interface CardItemProps {
	href: string;
	itemId: string;
	itemName: string;
	itemAtributes?: itemAttribute[];
}

export function CardItem({
	href,
	itemId,
	itemName,
	itemAtributes,
}: CardItemProps) {
	return (
		<a
			href={href}
			className="cursor-pointer overflow-hidden rounded-xl bg-white shadow-md transition-transform duration-300 ease-in-out"
		>
			<article>
				<div className="flex h-45 items-start bg-yellow-300 p-4">
					<span className="rounded-md bg-white p-2 font-bold text-sm shadow-md">
						{itemId}
					</span>
				</div>
				<div className="flex flex-col gap-4 p-4">
					<h3 className="font-bold text-xl">{itemName}</h3>
					{itemAtributes?.map((atribute) => (
						<div className="flex justify-between" key={atribute.atributeValue}>
							<span className="font-medium text-gray-400">
								{atribute.atributeLabel}
							</span>
							<span className="font-bold text-black">
								{atribute.atributeValue}
							</span>
						</div>
					))}
				</div>
			</article>
		</a>
	);
}
